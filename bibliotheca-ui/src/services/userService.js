import axios from "axios";

export default {
	async getAllUsers() {
		return await axios.get("/user");
	}
}