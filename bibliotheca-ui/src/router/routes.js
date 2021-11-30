import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Book from '../views/Book.vue'
import routeNames from "./routeNames";

const routes = [
	{
		path: '/',
		name: routeNames.HOME,
		component: Home,
		meta: {
			requiresAuth: true
		}
	},
	{
		path: '/book/:id',
		name: routeNames.BOOK,
		component: Book,
		meta: {
			requiresAuth: true
		}
	},
	{
		path: '/login',
		name: routeNames.LOGIN,
		component: Login,
		meta: {
			guest: true
		}
	},
	{
		path: '/register',
		name: routeNames.REGISTER,
		component: Register,
		meta: {
			guest: true
		}
	},
	{
		path: "/:pathMatch(.*)",
		name: routeNames.ERROR,
		component: Error,
		meta: {
			guest: true
		}
	}
]

export default routes;