import React from 'react'
import { Table } from 'semantic-ui-react'

function AttendanceTable(
    {
        attendances
    }) {

    let attendanceList
    if (attendances.length === 0) {
        attendanceList = (
            <Table.Row key='no-attendance'>
                <Table.Cell collapsing textAlign='center' colSpan='4'>No attendance</Table.Cell>
            </Table.Row>
        )
    } else {
        attendanceList = attendances.map(_ => {
            return (
                <Table.Row key={_.id}>
                    <Table.Cell>{_.id}</Table.Cell>
                    <Table.Cell>{_.class_id}</Table.Cell>
                    <Table.Cell>{_.class_name}</Table.Cell>
                    <Table.Cell>{_.date}</Table.Cell>
                    <Table.Cell>{_.is_present.toString()}</Table.Cell>
                </Table.Row>
            )
        })
    }

    return (
        <>
            <Table compact striped selectable>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell width={1}>id</Table.HeaderCell>
                        <Table.HeaderCell width={1}>class id</Table.HeaderCell>
                        <Table.HeaderCell width={3}>class Name</Table.HeaderCell>
                        <Table.HeaderCell width={3}>date</Table.HeaderCell>
                        <Table.HeaderCell width={1}>is_present</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>
                <Table.Body>
                    {attendanceList}
                </Table.Body>
            </Table>
        </>
    )
}

export default AttendanceTable