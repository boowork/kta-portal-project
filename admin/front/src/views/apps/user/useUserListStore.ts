export interface UserProperties {
  id: number
  fullName: string
  company: string
  role: string
  username?: string
  country: string
  contact: string
  email: string
  currentPlan: string
  status: string
  avatar: string
  billing: string
}

// Mock user data
const mockUsers: UserProperties[] = [
  {
    id: 1,
    fullName: 'Galen Slixby',
    company: 'Yotz PVT LTD',
    role: 'editor',
    username: 'gslixby0',
    country: 'El Salvador',
    contact: '(479) 232-9151',
    email: 'gslixby0@abc.net.au',
    currentPlan: 'enterprise',
    status: 'inactive',
    avatar: '',
    billing: 'Manual - Cash',
  },
  {
    id: 2,
    fullName: 'Halsey Redmore',
    company: 'Skinder PVT LTD',
    role: 'author',
    username: 'hredmore1',
    country: 'Albania',
    contact: '(472) 607-9137',
    email: 'hredmore1@imgur.com',
    currentPlan: 'team',
    status: 'pending',
    avatar: '',
    billing: 'Auto Debit',
  },
  {
    id: 3,
    fullName: 'Marjory Sicely',
    company: 'Oozz PVT LTD',
    role: 'maintainer',
    username: 'msicely2',
    country: 'Russia',
    contact: '(321) 264-4599',
    email: 'msicely2@who.int',
    currentPlan: 'enterprise',
    status: 'active',
    avatar: '',
    billing: 'Manual - PayPal',
  },
  {
    id: 4,
    fullName: 'Cyrill Risby',
    company: 'Oozz PVT LTD',
    role: 'maintainer',
    username: 'crisby3',
    country: 'China',
    contact: '(923) 690-6806',
    email: 'crisby3@wordpress.com',
    currentPlan: 'team',
    status: 'inactive',
    avatar: '',
    billing: 'Manual - Cash',
  },
  {
    id: 5,
    fullName: 'Maggy Hurran',
    company: 'Aimbo PVT LTD',
    role: 'subscriber',
    username: 'mhurran4',
    country: 'Pakistan',
    contact: '(669) 914-1078',
    email: 'mhurran4@yahoo.co.jp',
    currentPlan: 'enterprise',
    status: 'pending',
    avatar: '',
    billing: 'Manual - Cash',
  },
]

export const useUserListStore = () => {
  const users = ref<UserProperties[]>(mockUsers)

  const fetchUsers = () => {
    return Promise.resolve({ users: users.value, totalUsers: users.value.length })
  }

  const addUser = (user: Omit<UserProperties, 'id'>) => {
    const newUser = { ...user, id: Date.now() }

    users.value.push(newUser)

    return Promise.resolve(newUser)
  }

  const deleteUser = (userId: number) => {
    const index = users.value.findIndex(user => user.id === userId)
    if (index > -1)
      users.value.splice(index, 1)

    return Promise.resolve()
  }

  const getUserById = (userId: number) => {
    const foundUser = users.value.find(user => user.id === userId)

    return foundUser ? Promise.resolve(foundUser) : Promise.reject(new Error('User not found'))
  }

  return {
    users: readonly(users),
    fetchUsers,
    addUser,
    deleteUser,
    getUserById,
  }
}
