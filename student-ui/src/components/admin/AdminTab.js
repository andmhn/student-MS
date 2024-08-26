import React from 'react'
import { Tab } from 'semantic-ui-react'
import UserTable from './UserTable'
import ClassTable from './ClassTable'

function AdminTab(props) {
  const { handleInputChange } = props
  const { isUsersLoading, users, userUsernameSearch, handleDeleteUser, handleSearchUser } = props
  const { isClassLoading, classes, className, classNameSearch, handleAddClass, handleDeleteClass, handleSearchClass } = props
  const { classDescription, classFromTime, classToTime, classType } = props

  const panes = [
    {
      menuItem: { key: 'users', icon: 'users', content: 'Users' },
      render: () => (
        <Tab.Pane loading={isUsersLoading}>
          <UserTable
            users={users}
            userUsernameSearch={userUsernameSearch}
            handleInputChange={handleInputChange}
            handleDeleteUser={handleDeleteUser}
            handleSearchUser={handleSearchUser}
          />
        </Tab.Pane>
      )
    },
    {
      menuItem: { key: 'classes', icon: 'building', content: 'Classes' },
      render: () => (
        <Tab.Pane loading={isClassLoading}>
          <ClassTable
            classes={classes}
            classNameSearch={classNameSearch}
            handleInputChange={handleInputChange}
            handleAddClass={handleAddClass}
            handleDeleteClass={handleDeleteClass}
            handleSearchClass={handleSearchClass}
            className={className}
            classDescription={classDescription}
            classFromTime = {classFromTime}
            classToTime={classToTime}
            classType={classType}
          />
        </Tab.Pane>
      )
    }
  ]

  return (
    <Tab menu={{ attached: 'top' }} panes={panes} />
  )
}

export default AdminTab