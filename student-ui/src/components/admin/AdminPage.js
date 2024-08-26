import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { StudentApi } from '../misc/StudentApi'
import AdminTab from './AdminTab'
import { handleLogError } from '../misc/Helpers'

function AdminPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isAdmin = user.role === 'ADMIN'

  const [users, setUsers] = useState([])
  const [userUsernameSearch, setUserUsernameSearch] = useState('')
  const [isUsersLoading, setIsUsersLoading] = useState(false)

  const [classes, setClasses] = useState([])
  const [className, setClassName] = useState('')
  const [classDescription, setClassDescription] = useState('')
  const [classNameSearch, setClassNameSearch] = useState('')
  const [isClassLoading, setIsClassLoading] = useState(false)
  const [classFromTime, setClassFromTime] = useState('')
  const [classToTime, setClassToTime] = useState('')
  const [classType, setClassType] = useState('')

  const [attendances, setAttendances] = useState([])
  const [isAttendancesLoading, setIsAttendancesLoading] = useState(false)

  useEffect(() => {
    handleGetUsers()
    handleGetClasses()
    handleGetAttendances()
  }, [])


  ///// Attendance related

  const handleGetAttendances = async () => {
    try {
      setIsAttendancesLoading(true)
      const response = await StudentApi.getAllAttendances(user)
      setAttendances(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsAttendancesLoading(false)
    }
  }

  ///// class related

  const handleGetClasses = async () => {
    try {
      setIsClassLoading(true)
      const response = await StudentApi.getClasses()
      setClasses(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsClassLoading(false)
    }
  }

  const handleSearchClass = async () => {
    try {
      let response
      if (classNameSearch.length === 0) {
        response = await StudentApi.getClasses()
      } else {
        response = await StudentApi.getClassesByName(classNameSearch)
      }
      const classes = response.data
      console.log(classes)
      setClasses(classes)
    } catch (error) {
      handleLogError(error)
      setClasses([])
    }
  }

  const handleAddClass = async () => {
    try {
      const classPayload = {
        name: className.trim(),
        description: classDescription.trim(),
        type: classType.trim(),
        fromTime: classFromTime.trim(),
        toTime: classToTime.trim()
      }
      if (!(classPayload.description && classPayload.name)) {
        return
      }
      await StudentApi.addClass(user, classPayload)
      clearClassForm()
      await handleGetClasses()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleDeleteClass = async (classId) => {
    try {
      await StudentApi.deleteClass(user, classId)
      await handleGetClasses()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleInputChange = (e, { name, value }) => {
    if (name === 'userUsernameSearch') {
      setUserUsernameSearch(value)
    } else if (name === 'classNameSearch') {
      setClassNameSearch(value)
    } else if (name === 'classDescription') {
      setClassDescription(value)
    } else if (name === 'className') {
      setClassName(value)
    } else if (name === 'classFromTime') {
      setClassFromTime(value)
    } else if (name === 'classToTime') {
      setClassToTime(value)
    } else if (name === 'classType') {
      setClassType(value)
    }
  }

  //// user related

  const handleGetUsers = async () => {
    try {
      setIsUsersLoading(true)
      const response = await StudentApi.getUsers(user)
      const users = response.data
      setUsers(users)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsUsersLoading(false)
    }
  }

  const handleDeleteUser = async (username) => {
    try {
      await StudentApi.deleteUser(user, username)
      await handleGetUsers()
    } catch (error) {
      handleLogError(error)
    }
  }

  const handleSearchUser = async () => {
    try {
      const response = await StudentApi.getUsers(user, userUsernameSearch)
      const data = response.data
      const users = data instanceof Array ? data : [data]
      setUsers(users)
    } catch (error) {
      handleLogError(error)
      setUsers([])
    }
  }

  const clearClassForm = () => {
    setClassName('')
    setClassDescription('')
    setClassType('')
    setClassFromTime('')
    setClassToTime('')
  }

  if (!isAdmin) {
    return <Navigate to='/' />
  }

  return (
    <Container>
      <AdminTab
        isUsersLoading={isUsersLoading}
        users={users}
        userUsernameSearch={userUsernameSearch}
        handleDeleteUser={handleDeleteUser}
        handleSearchUser={handleSearchUser}

        handleInputChange={handleInputChange}

        isClassLoading={isClassLoading}
        handleAddClass={handleAddClass}
        handleDeleteClass={handleDeleteClass}
        handleSearchClass={handleSearchClass}
        classNameSearch={classNameSearch}
        classes={classes}
        className={className}
        classDescription={classDescription}
        classType={classType}
        classFromTime={classFromTime}
        classToTime={classToTime}

        isAttendancesLoading={isAttendancesLoading}
        attendances={attendances}
      />
    </Container>
  )
}

export default AdminPage