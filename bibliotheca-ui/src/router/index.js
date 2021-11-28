import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from "./routes";
import store from "../store/index";
import jwt_decode from "jwt-decode";
import RouteNames from "./routeNames";

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
}

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (store.getters.user.id == null) {
      next({
        name: RouteNames.LOGIN,
        params: { nextUrl: to.fullPath }
      });
    } else {
      const decoded = jwt_decode(store.getters.user.token);
      const exp = decoded.exp * 1000;
      if (new Date().getTime() > exp) {
        store.dispatch('setUser', {
          id: null,
          username: null,
          email: null,
          token: null
        });
        next({
          name: RouteNames.LOGIN,
          params: { nextUrl: to.fullPath }
        });
      }
      next();
    }
  } else if (to.matched.some(record => record.meta.guest)) {
    if (store.getters.user.token == null) {
      next();
    } else {
      next({
        name: RouteNames.HOME
      });
    }
  } else {
    next();
  }
});

export default router
