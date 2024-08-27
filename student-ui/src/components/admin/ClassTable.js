import React from 'react'
import { Button, Form, Grid, Icon, Input, Segment, Table } from 'semantic-ui-react'

import { Component } from 'react'
import { Confirm } from 'semantic-ui-react'

class ConfirmDelete extends Component{
  state = { open: false }

  show = () => this.setState({ open: true })
  handleConfirm = () => {
    this.props.handleDeleteClass(this.props.classItem.id)
    this.setState({ open: false })
  }
  handleCancel = () => this.setState({ open: false })

  render() {
    const { open } = this.state

    return (
      <div>

        <Button
          circular
          color='red'
          size='small'
          icon='trash'
          onClick={this.show}
        />

        <Confirm
          open={open}
          onCancel={this.handleCancel}
          onConfirm={this.handleConfirm}
        />
      </div>
    )
  }
}

function ClassForm(
  {
    className,
    handleInputChange,
    handleAddClass,
    classDescription,
    classFromTime,
    classToTime,
    classType
  }) {

  const createBtnDisabled = classDescription.trim() === '' || className.trim() === ''

  return (
    <Form onSubmit={handleAddClass}>
      <Segment size='small'>
        <Form.Input
          name='className'
          placeholder='Name *'
          value={className}
          onChange={handleInputChange}
        />
        <Form.Input
          name='classDescription'
          placeholder='Class Description *'
          value={classDescription}
          onChange={handleInputChange}
        />
        <Form.Input
          name='classType'
          placeholder='Class Type *'
          value={classType}
          onChange={handleInputChange}
        />
        <Form.Input
          name='classFromTime'
          placeholder='Class From Time *'
          value={classFromTime}
          onChange={handleInputChange}
        />
        <Form.Input
          name='classToTime'
          placeholder='Class To Time *'
          value={classToTime}
          onChange={handleInputChange}
        />
        <Button icon labelPosition='right' disabled={createBtnDisabled}>
          Create New Class<Icon name='add' />
        </Button>
      </Segment>
    </Form>
  )
}

function ClassTable(
  {
    classes,
    className,
    classNameSearch,
    handleInputChange,
    handleAddClass,
    handleDeleteClass,
    handleSearchClass,
    classDescription,
    classFromTime,
    classToTime,
    classType
  }) {

  let classList
  if (classes.length === 0) {
    classList = (
      <Table.Row key='no-class'>
        <Table.Cell collapsing textAlign='center' colSpan='4'>No class</Table.Cell>
      </Table.Row>
    )
  } else {
    classList = classes.map(classItem => {
      return (
        <Table.Row key={classItem.id}>
          <Table.Cell collapsing>
            <ConfirmDelete
              handleDeleteClass = {handleDeleteClass}
              classItem = {classItem}
            />
          </Table.Cell>
          <Table.Cell>{classItem.id}</Table.Cell>
          <Table.Cell>{classItem.name}</Table.Cell>
          <Table.Cell>{classItem.description}</Table.Cell>
          <Table.Cell>{classItem.fromTime} - {classItem.toTime}</Table.Cell>
        </Table.Row>
      )
    })
  }

  return (
    <>
      <Grid stackable divided>
        <Grid.Column columns='2'>
          <Grid.Column>
            <ClassForm
              className={className}
              classDescription={classDescription}
              handleInputChange={handleInputChange}
              handleAddClass={handleAddClass}
              classFromTime={classFromTime}
              classToTime={classToTime}
              classType={classType}
            />
          </Grid.Column>
          <hr></hr>
          <Grid.Column width='5'>
            <Form onSubmit={handleSearchClass}>
              <Input
                action={{ icon: 'search' }}
                name='classNameSearch'
                placeholder='Search by Class Name'
                value={classNameSearch}
                onChange={handleInputChange}
              />
            </Form>
          </Grid.Column>
        </Grid.Column>
      </Grid>
      <Table compact striped selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={1} />
            <Table.HeaderCell width={1}>id</Table.HeaderCell>
            <Table.HeaderCell width={4}>name</Table.HeaderCell>
            <Table.HeaderCell width={8}>description</Table.HeaderCell>
            <Table.HeaderCell width={4}>time</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {classList}
        </Table.Body>
      </Table>
    </>
  )
}

export default ClassTable