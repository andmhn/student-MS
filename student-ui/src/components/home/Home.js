import React, { useState, useEffect } from 'react'
import { Statistic, Icon, Grid, Container, Image, Segment, Dimmer, Loader } from 'semantic-ui-react'
import { StudentApi } from '../misc/StudentApi'
import { handleLogError } from '../misc/Helpers'
import ClassList from './ClassList.js'

function Home() {
  const [numberOfUsers, setNumberOfUsers] = useState(0)
  const [numberOfClasses, setNumberOfClasses] = useState(0)
  const [isLoading, setIsLoading] = useState(false)

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
      if(classNameSearch.length === 0) {
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

  const fetchPublicData = async () => {
    setIsLoading(true)
    try {
      const responseUsers = await StudentApi.numberOfUsers()
      setNumberOfUsers(responseUsers.data)

      const responseClasses = await StudentApi.numberOfClasses()
      setNumberOfClasses(responseClasses.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchPublicData()
    handleGetClasses()
  }, [])

  if (isLoading) {
    return (
      <Segment basic style={{ marginTop: window.innerHeight / 2 }}>
        <Dimmer active inverted>
          <Loader inverted size='huge'>Loading</Loader>
        </Dimmer>
      </Segment>
    )
  }

  return (
    <Container text>
      <Grid stackable columns={2}>
        <Grid.Row>
          <Grid.Column textAlign='center'>
            <Segment color='blue'>
              <Statistic>
                <Statistic.Value><Icon name='user' color='grey' />{numberOfUsers}</Statistic.Value>
                <Statistic.Label>Users</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
          <Grid.Column textAlign='center'>
            <Segment color='blue'>
              <Statistic>
                <Statistic.Value><Icon name='building' color='grey' />{numberOfClasses}</Statistic.Value>
                <Statistic.Label>Classes</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
        </Grid.Row>
      </Grid>

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

export default Home