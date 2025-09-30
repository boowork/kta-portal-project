import dashboard from './dashboard'
import users from './users'
import partners from './partners'

export default [
  ...dashboard,
  ...users,
  ...partners,
  {
    title: 'Second page',
    to: { name: 'second-page' },
    icon: { icon: 'bx-file-blank' },
  },
]
