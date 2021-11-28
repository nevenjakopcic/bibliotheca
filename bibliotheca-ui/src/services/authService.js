import axios from "axios";

export default {
	async login(username, password) {
		return await axios.post("/auth/login", {
			username,
			password
		});
	},
	async register(username, email, password) {
		return await axios.post("/auth/register", {
			username,
			email,
			password
		});
	}
};