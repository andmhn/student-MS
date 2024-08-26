import React from 'react'
import { Grid, Header, Form, Icon, Image, Input, Item, Segment } from 'semantic-ui-react'

function ClassList({ isClassesLoading, classNameSearch, classes, handleInputChange, handleSearchClass }) {
  let classList
  if (classes.length === 0) {
    classList = <Item key='no-class'>No Classes</Item>
  }
  else {
    classList = classes.map(classItem => {
      return (
        <Item key={classItem.id}>
          <Item.Content>
          <Item.Extra>{classItem.id}</Item.Extra>
          <Item.Header>{classItem.name}</Item.Header>
          <Item.Meta>{classItem.type}</Item.Meta>
          <Item.Description>{classItem.fromTime} - {classItem.toTime}</Item.Description>
          <Item.Description>{classItem.description}</Item.Description>
          </Item.Content>
        </Item>
      )
    })
  }

  return (
    <Segment loading={isClassesLoading} color='blue'>
      <Grid stackable divided>
        <Grid.Row columns='2'>
          <Grid.Column width='4'>
            <Header as='h2'>
              <Icon name='building' />
              <Header.Content>Classes</Header.Content>
            </Header>
          </Grid.Column>
          <Grid.Column>
            <Form onSubmit={handleSearchClass}>
              <Input
                action={{ icon: 'search' }}
                name='classNameSearch'
                placeholder='Search by Class Name'
                value={classNameSearch}
                onChange={handleInputChange}
                type='string'
              />
            </Form>
          </Grid.Column>
        </Grid.Row>
      </Grid>
      <Item.Group divided unstackable relaxed>
        {classList}
      </Item.Group>
    </Segment>
  )
}

export default ClassList