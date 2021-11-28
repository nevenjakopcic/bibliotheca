import Vue from 'vue'
import axios from 'axios'
import { API_URL } from '@/constants';
import store from '../store/index';

Vue.prototype.$axios = axios

axios.interceptors.request.use((config) => {
  config.url = API_URL + config.url;
  config.validateStatus = false;
  config.headers.common['Authorization'] = store.getters.user.token && `Bearer ${store.getters.user.token}`;
  return config;
}, function (error) {
  return Promise.reject(error);
});