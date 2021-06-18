export const navigationRoutes = {
  root: {
    name: '/',
    displayName: 'navigationRoutes.home',
  },
  routes: [
    {
      name: 'dashboard',
      displayName: 'menu.dashboard',
      meta: {
        iconClass: 'vuestic-iconset vuestic-iconset-dashboard',
      },
    },
    {
      name: 'users',
      displayName: 'menu.users',
      meta: {
        iconClass: 'vuestic-iconset vuestic-iconset-user',
      },
      disabled: true,
    },
    {
      name: 'departments',
      displayName: 'menu.departments',
      meta: {
        iconClass: 'vuestic-iconset vuestic-iconset-ui-elements',
      },
      disabled: true,
    },
    {
      name: 'customers',
      displayName: 'menu.customers',
      meta: {
        iconClass: 'fa fa-child',
      },
      disabled: true,
    },
    {
      name: 'projects',
      displayName: 'menu.projects',
      meta: {
        iconClass: 'fa fa-briefcase',
      },
      disabled: true,
    },
    {
      name: 'timesheets',
      displayName: 'menu.timesheet',
      meta: {
        iconClass: 'fa fa-calendar-check-o',
      },
      disabled: true,
    },
    {
      name: 'customer-edit',
      displayName: 'menu.notifications',
      meta: {
        iconClass: 'fa fa-bell',
      },
      children: [
        {
          name: 'notice-edit',
          displayName: 'menu.notification-create',
        },
      ],
      disabled: true,
    },
    {
      name: 'pages',
      displayName: 'menu.pages',
      meta: {
        iconClass: 'vuestic-iconset vuestic-iconset-files',
      },
      disabled: true,
      children: [
        {
          name: 'login',
          displayName: 'Login/Signup',
        },
        {
          name: '404-pages',
          displayName: '404 Pages',
        },
      ],
    },
  ],
}
