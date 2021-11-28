import { mapGetters, mapActions } from "vuex";

export default {
	computed: {
		...mapGetters(["user", "isAdmin"])
	},
	methods: {
		...mapActions(["setUser"])
	}
}