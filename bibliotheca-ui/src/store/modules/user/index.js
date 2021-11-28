import { AUTH_ROLE } from "@/constants/enumerations";

export default {
	state: {
		user: {
			id: null,
			username: null,
			email: null,
			token: null,
			role: null
		}
	},
	mutations: {
		setUser(state, payload) {
			state.user = payload;
		}
	},
	actions: {
		setUser({ commit }, payload) {
			commit("setUser", payload);
		}
	},
	getters: {
		user: state => state.user,
		isAdmin: state => state.user.role == AUTH_ROLE.ROLE_ADMIN
	},
}