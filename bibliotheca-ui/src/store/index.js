import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
import user from "./modules/user";
import general from "./modules/general";
import membership from "./modules/membership"

Vue.use(Vuex)

export default new Vuex.Store({
	modules: { user, general, membership },
	plugins: [createPersistedState()]
})
