#!/usr/bin/env python3
"""
Header 관리 시스템
- versions/headver.txt를 사용한 버전 관리
- 다음 header 생성 기능
- HeadVer 시스템과 연동
"""

import os
import re
import subprocess
from datetime import datetime
from dataclasses import dataclass
from typing import List, Optional

@dataclass
class HeaderEntry:
    timestamp: datetime
    head: int
    yearweek: int  
    build: int
    description: str
    version: str
    
    @classmethod
    def from_line(cls, line: str) -> 'HeaderEntry':
        """headver.txt 한 줄을 파싱"""
        if line.startswith('#') or not line.strip():
            return None
            
        parts = line.strip().split('|')
        if len(parts) != 3:
            return None
            
        timestamp_str, version_str, description = parts
        timestamp = datetime.fromisoformat(timestamp_str)
        
        # version 파싱 (head.yearweek.build)
        version_parts = version_str.split('.')
        head = int(version_parts[0])
        yearweek = int(version_parts[1])
        build = int(version_parts[2])
        
        return cls(
            timestamp=timestamp,
            head=head,
            yearweek=yearweek,
            build=build,
            description=description,
            version=version_str
        )

class HeaderManager:
    def __init__(self, versions_dir: str = "."):
        self.versions_dir = versions_dir
        self.headver_file = os.path.join(versions_dir, "headver.txt")
        self.headver_script = "/Users/bjm/bin/headver.sh"
        
    def parse_headver_file(self) -> Optional[HeaderEntry]:
        """versions/headver.txt에서 현재 버전만 파싱 (단순히 현재 버전만 저장된 경우)"""
        try:
            with open(self.headver_file, 'r') as f:
                content = f.read().strip()
                if content:
                    # 버전만 있는 경우 (예: "0.2538.1")
                    version = content.strip()
                    if '.' in version and len(version.split('.')) >= 3:
                        parts = version.split('.')
                        head = int(parts[0])
                        yearweek = int(parts[1])
                        build_part = parts[2].split('+')[0]  # suffix 제거
                        build = int(build_part)
                        return HeaderEntry(
                            timestamp=datetime.now(),
                            head=head,
                            yearweek=yearweek,
                            build=build,
                            description=f"Version {version}",
                            version=version
                        )
        except (FileNotFoundError, ValueError, IndexError):
            pass
        
        return None
    
    def get_current_version(self) -> Optional[HeaderEntry]:
        """현재 최신 버전 반환"""
        return self.parse_headver_file()
    
    def get_current_head(self) -> int:
        """현재 head 번호 반환 (최신 버전 기준)"""
        current = self.get_current_version()
        return current.head if current else 0
    
    def get_current_yearweek(self) -> int:
        """headver.sh를 사용해 현재 yearweek 계산"""
        try:
            result = subprocess.run([
                self.headver_script, 
                "head=0", 
                "build=1"
            ], capture_output=True, text=True)
            
            output = result.stdout
            for line in output.split('\n'):
                if line.startswith('version:'):
                    version = line.split(':')[1].strip()
                    return int(version.split('.')[1])
                    
        except Exception as e:
            print(f"Error getting yearweek: {e}")
            # 폴백: 현재 날짜 기준 yearweek 계산
            now = datetime.now()
            year = now.year % 100  # 2자리 연도
            week = now.isocalendar()[1]  # ISO 주차
            return int(f"{year:02d}{week:02d}")
    
    def get_next_build_number(self, head: int, yearweek: int) -> int:
        """다음 빌드 번호 계산 (주차 변경 시 1로 리셋)"""
        current = self.get_current_version()
        
        if not current:
            return 1
        
        # 같은 head와 yearweek인 경우 빌드 번호 증가
        if current.head == head and current.yearweek == yearweek:
            return current.build + 1
        else:
            # head가 다르거나 yearweek가 변경된 경우 1부터 시작
            return 1
    
    def generate_next_header(self, description: str = "", head: Optional[int] = None, suffix: str = "") -> str:
        """다음 헤더 버전 생성"""
        # head가 지정되지 않으면 현재 head 사용
        if head is None:
            head = self.get_current_head()
        
        current_yearweek = self.get_current_yearweek()
        if current_yearweek is None:
            raise Exception("Failed to get current yearweek")
        
        next_build = self.get_next_build_number(head, current_yearweek)
        
        # headver.sh로 정확한 버전 생성
        cmd = [self.headver_script, f"head={head}", f"build={next_build}"]
        if suffix:
            cmd.append(f"suffix={suffix}")
            
        result = subprocess.run(cmd, capture_output=True, text=True)
        
        version = None
        for line in result.stdout.split('\n'):
            if line.startswith('version:'):
                version = line.split(':')[1].strip()
                break
        
        if not version:
            raise Exception("Failed to generate version")
        
        # description이 비어있으면 기본값 설정
        if not description.strip():
            description = f"Version {version}"
        
        # versions/headver.txt에 기록
        self.add_version_entry(version, description)
        
        return version
    
    def add_version_entry(self, version: str, description: str):
        """versions/headver.txt에 현재 버전만 저장"""
        os.makedirs(self.versions_dir, exist_ok=True)
        with open(self.headver_file, 'w') as f:
            f.write(version)
    
    def set_head(self, new_head: int):
        """새로운 head 번호 설정 (다음 버전부터 적용)"""
        print(f"다음 버전부터 head {new_head}가 사용됩니다.")
        print(f"generate_next_header(head={new_head})로 새 버전을 생성하세요.")
    
    def list_versions(self, limit: int = 10):
        """현재 버전 정보 출력"""
        current = self.get_current_version()
        if current:
            print(f"현재 버전: {current.version}")
            print(f"설명: {current.description}")
        else:
            print("버전 정보가 없습니다.")
    
    def get_version_stats(self):
        """버전 통계 정보"""
        current = self.get_current_version()
        
        if not current:
            print("버전 히스토리가 없습니다.")
            return
        
        print(f"현재 버전: {current.version}")
        print(f"현재 Head: {current.head}")
        print(f"현재 Yearweek: {current.yearweek}")
        print(f"현재 Build: {current.build}")
        print(f"설명: {current.description}")

def main():
    """CLI 인터페이스"""
    import sys
    
    manager = HeaderManager()
    
    if len(sys.argv) < 2:
        print("Usage: python header.py <command> [args]")
        print("Commands:")
        print("  next [description] [head] [suffix] - Generate next header version")
        print("  current                            - Show current version")
        print("  list [limit]                       - List recent versions")
        print("  stats                              - Show version statistics")
        print("  set-head <number>                  - Set head for next versions")
        print("")
        print("Examples:")
        print("  python header.py next                    # Auto-increment without description")
        print("  python header.py next \"Event System\"")
        print("  python header.py next \"Trading Core\" 1")
        print("  python header.py next \"iOS Support\" 0 iOS")
        return
    
    cmd = sys.argv[1]
    
    if cmd == "next":
        description = sys.argv[2] if len(sys.argv) > 2 else ""
        head = int(sys.argv[3]) if len(sys.argv) > 3 else None
        suffix = sys.argv[4] if len(sys.argv) > 4 else ""
        
        try:
            version = manager.generate_next_header(description, head, suffix)
            print(f"Generated version: {version}")
            
            # 실제 저장된 description 출력 (자동 생성된 경우 포함)
            current = manager.get_current_version()
            if current:
                print(f"Description: {current.description}")
        except Exception as e:
            print(f"Error: {e}")
    
    elif cmd == "current":
        current = manager.get_current_version()
        if current:
            print(f"Current version: {current.version}")
            print(f"Description: {current.description}")
            print(f"Timestamp: {current.timestamp}")
            print(f"Head: {current.head}")
            print(f"Yearweek: {current.yearweek}")
            print(f"Build: {current.build}")
        else:
            print("No versions found")
    
    elif cmd == "list":
        limit = int(sys.argv[2]) if len(sys.argv) > 2 else 10
        manager.list_versions(limit)
    
    elif cmd == "stats":
        manager.get_version_stats()
    
    elif cmd == "set-head":
        if len(sys.argv) != 3:
            print("Usage: python header.py set-head <number>")
            return
            
        head = int(sys.argv[2])
        manager.set_head(head)
    
    else:
        print(f"Unknown command: {cmd}")

if __name__ == "__main__":
    main()