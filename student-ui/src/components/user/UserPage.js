import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { StudentApi } from '../misc/StudentApi'
import { handleLogError } from '../misc/Helpers'
import UserTab from './UserTab'

function UserPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isUser = user.role === 'USER'

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
    handleGetClasses()
    handleGetAttendances()
  }, [])


  ///// class related

  const handleGetClasses = async () => {
    try {
      setIsClassLoading(true)
      const response = await StudentApi.getClasses()

      setClasses(
        await Promise.all(
          // fetching an attribute "is_present_today"

          response.data.map(async c => {
            const r = await StudentApi.hasClassEntryForToday(user, c.id)
            c.is_present_today = r.data
            return c
          })
        )
      )

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
      setClasses(response.data)
    } catch (error) {
      handleLogError(error)
      setClasses([])
    }
  }

  ///// Attendance related

  const handleGetAttendances = async () => {
    try {
      setIsAttendancesLoading(true)
      const response = await StudentApi.getUserAttendances(user)
      setAttendances(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsAttendancesLoading(false)
    }
  }
  const handleAttendClass = async (classId, isPresent) => {
    let attendancePayload = {
      "class_id": classId,
      "date": new Date().toJSON().slice(0, 10),
      "is_present": isPresent,
      "absent_reason": ""
    }
    try {
      let hasEntry = await StudentApi.hasClassEntryForToday(user, classId)
      if (hasEntry.data === false) {
        await StudentApi.addAttendance(user, attendancePayload)
      } else {
        alert("Already registered attendance for Today")
      }
    } catch (error) {
      handleLogError(error)
      setClasses([])
    }
  }

  const handleInputChange = (e, { name, value }) => {
    if (name === 'classNameSearch') {
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

  if (!isUser) {
    return <Navigate to='/' />
  }

  return (
    <Container>
      <UserTab
        handleInputChange={handleInputChange}
        isClassLoading={isClassLoading}
        handleSearchClass={handleSearchClass}
        classNameSearch={classNameSearch}
        classes={classes}
        className={className}
        classDescription={classDescription}
        classType={classType}
        classFromTime={classFromTime}
        classToTime={classToTime}
        handleAttendClass={handleAttendClass}
        attendances={attendances}
        isAttendancesLoading={isAttendancesLoading}
      />
    </Container>
  )
}

export default UserPage