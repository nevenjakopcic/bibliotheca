import { isBefore } from "date-fns";
import { AUTH_ROLE } from "@/constants/enumerations";
import store from "../../index";

export default {
  state: {
    membership: null
  },
  mutations: {
    setMembership(state, payload) {
      state.membership = payload;
    }
  },
  actions: {
    setMembership({ commit }, payload) {
      commit("setMembership", payload);
    }
  },
  getters: {
    membership: state => state.membership,
    validMembership: state => {
      if (store.getters.user.role === AUTH_ROLE.ROLE_ADMIN) {
        return true;
      } else {
        if (state.membership) {
          return isBefore(
            new Date(),
            new Date(state.membership.validUntil)
          );
        }
        return false;
      }
    }
  },
}