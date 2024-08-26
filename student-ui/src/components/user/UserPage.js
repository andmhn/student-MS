import React, { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { StudentApi } from '../misc/StudentApi'
import { handleLogError } from '../misc/Helpers'
import ClassList from '../home/ClassList'

function UserPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isUser = user.role === 'USER'

  // classes--------------------------------------------------
  //----------------------------------------------------------
  const [classes, setClasses] = useState([])
  const [classNameSearch, setclassNameSearch] = useState('')
  const [isClassesLoading, setIsClassesLoading] = useState(false)

  const handleInputChange = (e, { name, value }) => {
    if (name === 'classNameSearch') {
      setclassNameSearch(value)
    }
  }

  const handleGetClasses = async () => {
    try {
      setIsClassesLoading(true)
      const response = await StudentApi.getClasses()
      setClasses(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsClassesLoading(false)
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


  useEffect(() => {
    handleGetClasses()
  }, [])


  if (!isUser) {
    return <Navigate to='/' />
  }

  return (
    <Container text>
      <ClassList
        isClassesLoading={isClassesLoading}
        classNameSearch={classNameSearch}
        classes={classes}
        handleInputChange={handleInputChange}
        handleSearchClass={handleSearchClass}
      />
    </Container>
  )
}

export default UserPage