export const paginationMeta = <T extends { page: number; itemsPerPage: number }>(options: T, total: number) => {
  const start = (options.page - 1) * options.itemsPerPage + 1
  const end = Math.min(options.page * options.itemsPerPage, total)

  return `총 ${total}개 중 ${total === 0 ? 0 : start} - ${end}개 표시`
}
