# close sequence

1. check changed files and create github issue
2. git add . && git commit -m {message} && git push origin dev
3. create pull request with start with issue number `{issue number}{title}`
4. gh pr merge {pull reuquest number} --merge --delete-branch=true
5. git worktree list 
6. cd worktree dev directory
7. git pull origin dev
