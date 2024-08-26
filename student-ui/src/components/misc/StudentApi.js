import axios from 'axios'
import { config } from '../../Constants'

export const StudentApi = {
  authenticate,
  signup,
  numberOfUsers,
  getUsers,
  deleteUser,

  numberOfClasses,
  getClasses,
  getClassesByName,
  deleteClass,
  addClass,
  
  addAttendance,
  getUserAttendances,
  hasClassEntryForToday,
  getAllAttendances
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL
})

// -- Helper functions

function basicAuth(user) {
  return `Basic ${user.authdata}`
}

// -----------------------
//  account related
//------------------------

function authenticate(username, password) {
  return instance.post('/auth/authenticate', { username, password }, {
    headers: { 'Content-type': 'application/json' }
  })
}

function signup(user) {
  return instance.post('/auth/signup', user, {
    headers: { 'Content-type': 'application/json' }
  })
}

function numberOfUsers() {
  return instance.get('/public/numberOfUsers')
}

function getUsers(user, username) {
  const url = username ? `/api/users/${username}` : '/api/users'
  return instance.get(url, {
    headers: { 'Authorization': basicAuth(user) }
  })
}

function deleteUser(user, username) {
  return instance.delete(`/api/users/${username}`, {
    headers: { 'Authorization': basicAuth(user) }
  })
}

// -----------------------
//  Classes related
//------------------------

function numberOfClasses() {
  return instance.get('/public/numberOfClasses')
}

function getClasses(id) {
  const url = id ? `/public/classes/${id}` : '/public/classes'
  return instance.get(url)
}

function getClassesByName(name) {
  const url =`/public/classes/search/${name}`
  return instance.get(url)
}

function deleteClass(admin, id) {
  return instance.delete(`/api/classes/${id}`, {
    headers: { 'Authorization': basicAuth(admin) }
  })
}

function addClass(admin, classPayload) {
  return instance.post('/api/classes', classPayload, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': basicAuth(admin)
    }
  })
}

function hasClassEntryForToday(user, classId) {
  return instance.get(`/api/classes/${classId}/hasUserEntryForToday`, {
    headers: { 'Authorization': basicAuth(user) }
  })
}


// -----------------------
//  Attendance related
//------------------------

function addAttendance(user, attendancePayload) {
  return instance.post('/api/classes/attendances', attendancePayload, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': basicAuth(user)
    }
  })
}

function getUserAttendances(user) {
  return instance.get('/api/classes/attendances/me', {
    headers: {
      'Authorization': basicAuth(user)
    }
  })
}

function getAllAttendances(admin) {
  return instance.get('/api/classes/attendances', {
    headers: {
      'Authorization': basicAuth(admin)
    }
  })
}
