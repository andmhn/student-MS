import React from 'react'
import { Tab } from 'semantic-ui-react'
import ClassTable from './ClassTable'
import AttendanceTable from './AttendanceTable'

function UserTab(props) {
  const { handleInputChange } = props
  const { isClassLoading, classes, className, classNameSearch, handleAddClass, handleAttendClass, handleSearchClass } = props
  const { classDescription, classFromTime, classToTime, classType } = props

  const {isAttendancesLoading, attendances} = props

  const panes = [
    {
      menuItem: { key: 'classes', icon: 'building', content: 'Classes' },
      render: () => (
        <Tab.Pane loading={isClassLoading}>
          <ClassTable
            classes={classes}
            classNameSearch={classNameSearch}
            handleInputChange={handleInputChange}
            handleAddClass={handleAddClass}
            handleAttendClass={handleAttendClass}
            handleSearchClass={handleSearchClass}
            className={className}
            classDescription={classDescription}
            classFromTime = {classFromTime}
            classToTime={classToTime}
            classType={classType}
          />
        </Tab.Pane>
      )
    },
    {
      menuItem: { key: 'attendances', icon: 'tasks', content: 'My Attendances' },
      render: () => (
        <Tab.Pane loading={isAttendancesLoading}>
          <AttendanceTable
            attendances={attendances}
          />
        </Tab.Pane>
      )
    }
  ]

  return (
    <Tab menu={{ attached: 'top' }} panes={panes} />
  )
}

export default UserTab