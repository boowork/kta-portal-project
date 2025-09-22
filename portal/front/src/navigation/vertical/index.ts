import dashboard from './dashboard'
import users from './users'

export default [
  ...dashboard,
  ...users,
  {
    title: 'Second page',
    to: { name: 'second-page' },
    icon: { icon: 'bx-file-blank' },
  },
]
