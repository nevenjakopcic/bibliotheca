import axios from "axios";

export default {
	async getMembershipOfUser(userId) {
		return await axios.get(`/membership/${userId}`);
	},
	async extendMembership(userId) {
		return await axios.post(`/membership/${userId}`)
	}
};