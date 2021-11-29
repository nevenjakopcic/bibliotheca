<template>
  <main class="center-page d-flex flex-column justify-center align-center">
    <v-card class="rounded-lg elevation-2" :max-width="width">
      <div class="grey lighten-5 py-2">
        <v-list-item>
          <v-list-item-avatar class="d-none d-sm-block">
            <v-icon>mdi-library</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="font-weight-bold">
              Login to Bibliotheca
            </v-list-item-title>
            <v-list-item-subtitle class="d-none d-sm-block">
              Login with you credentials
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </div>
      <v-divider/>
      <v-card-text class="pt-5">
        <validation-observer v-slot="{ handleSubmit }">
          <form @submit.prevent="handleSubmit(login)">
            <v-row>
              <v-col cols="12">
                <validation-provider
                    vid="username"
                    name="Username"
                    rules="required"
                    v-slot="{ errors, valid, untouched, required, failed }"
                >
                  <v-text-field
                      id="username"
                      prepend-icon="mdi-account"
                      v-model="username"
                      :error-messages="errors"
                      :hide-details="valid || (untouched && !failed)"
                      dense
                  >
                    <template #label>
                      <required-icon v-show="required"/>
                      <span>Username</span>
                    </template>
                  </v-text-field>
                </validation-provider>
              </v-col>
              <v-col cols="12">
                <validation-provider
                    vid="password"
                    name="Password"
                    rules="required"
                    v-slot="{ errors, valid, untouched, required, failed }"
                >
                  <v-text-field
                      id="password"
                      :type="showPassword ? 'text' : 'password'"
                      v-model="password"
                      prepend-icon="mdi-lock"
                      :error-messages="errors"
                      :hide-details="valid || (untouched && !failed)"
                      dense
                  >
                    <template #label>
                      <required-icon v-show="required"/>
                      <span>{{ "Password" }}</span>
                    </template>
                    <template #append>
                      <v-btn icon small @click="showPassword = !showPassword">
                        <v-icon
                            v-text="!showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                        />
                      </v-btn>
                    </template>
                  </v-text-field>
                </validation-provider>
              </v-col>
              <v-col cols="12" class="text-center text-md-right mt-2">
                <v-btn
                    :loading="loading"
                    :disabled="loading"
                    small
                    type="submit"
                    color="primary"
                >
                  {{ "Submit" }}
                </v-btn>
              </v-col>
            </v-row>
          </form>
        </validation-observer>
      </v-card-text>
    </v-card>
    <span class="mt-4 text-subtitle-2">
      {{ "Don't have an account?" }} {{ "Register " }}
      <router-link :to="{ name: RouteNames.REGISTER }">
        {{ "here." }}
      </router-link>
    </span>
  </main>
</template>

<script>
import AuthService from "../services/authService";
import MembershipService from "../services/membershipService";
import {mapActions} from "vuex";
import RouteNames from "../router/routeNames";
import {AUTH_ROLE} from "@/constants/enumerations";

export default {
  name: "Login",
  data: () => ({
    loading: false,
    username: null,
    password: null,
    showPassword: false,
    RouteNames
  }),
  computed: {
    width() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          return 300;
        case "sm":
          return 400;
        case "md":
          return 500;
        case "lg":
          return 600;
        case "xl":
          return 700;
        default:
          return 500;
      }
    }
  },
  methods: {
    ...mapActions(["setUser", "setMembership"]),
    async login() {
      this.loading = true;
      const response = await AuthService.login(this.username, this.password);
      if (response.status >= 400) {
        const {data} = response;
        this.$emit("show-snackbar", {
          color: "error",
          message: data.error
        });
        this.loading = false;
      } else {
        const {
          data: {data}
        } = response;

        this.setUser({
          id: data.id,
          username: this.username,
          email: data.email,
          token: data.jwtToken,
          role: data.role
        });

        if (data.role === AUTH_ROLE.ROLE_USER) {
          const responseMembership = await MembershipService.getMembershipOfUser(data.id);

          if (responseMembership.status >= 400) {
            this.setMembership(null);
          } else {
            const {
              data: {data}
            } = responseMembership;
            this.setMembership(data);
          }
        } else {
          this.setMembership(null);
        }

        this.$emit("show-snackbar", {
          color: "success",
          message: "You have logged in successfully"
        });

        this.$router.push({name: "home"});
      }
      this.loading = false;
    }
  }
};
</script>

<style scoped lang="sass">
.center-page
  width: 100vw
  height: 100vh
</style>
