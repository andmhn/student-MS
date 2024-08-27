import React, { Component } from 'react'
import { Button, Form, Grid, Input, Table } from 'semantic-ui-react'

import { GridColumn, Popup } from 'semantic-ui-react'

class PopupAttendance extends Component {
    state = { isDisabled: this.props.disabled }

    hide = () => this.setState({ isDisabled: true })

    render() {
        const { isDisabled } = this.state
        return (
            <Popup disabled = {isDisabled} wide trigger={
                <Button
                    circular
                    color='blue'
                    size='small'
                    icon='checked calendar'
                    content='Attend'
                    disabled={isDisabled}
                />
            } on='click'>
                <Grid divided columns='equal'>
                    <GridColumn>
                        <Popup
                            trigger={
                                <Button
                                    color='green'
                                    content='Present'
                                    fluid
                                    onClick={() => {
                                        this.hide()
                                        this.props.handleAttendClass(this.props.classId, true);
                                    }}
                                />
                            }
                            content='Present Today for selected class.'
                            position='top center'
                            size='tiny'
                            inverted
                        />
                    </GridColumn>
                    <GridColumn >
                        <Popup
                            trigger={
                                <Button
                                    color='red'
                                    content='Absent'
                                    fluid
                                    onClick={() => {
                                        this.hide()
                                        this.props.handleAttendClass(this.props.classId, false);
                                    }}
                                />
                            }
                            content='Absent Today for selected class.'
                            position='top center'
                            size='tiny'
                            inverted
                        />
                    </GridColumn>
                </Grid>
            </Popup>
        )
    }
}


function ClassTable(
    {
        classes,
        classNameSearch,
        handleInputChange,
        handleAttendClass,
        handleSearchClass
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
                        <PopupAttendance
                            classId={classItem.id}
                            handleAttendClass={handleAttendClass}
                            disabled={(classItem.is_present_today)}
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
                        <Table.HeaderCell width={1}>attend</Table.HeaderCell>
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