import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
import user from "./modules/user";
import general from "./modules/general";

Vue.use(Vuex)

export default new Vuex.Store({
	modules: { user, general },
	plugins: [createPersistedState()]
})
