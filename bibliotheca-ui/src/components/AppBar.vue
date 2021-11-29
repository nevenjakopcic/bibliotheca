<template>
  <v-app-bar
      app
      :class="{
      white: true
    }"
      class="elevation-1"
      clipped-left
  >
    <v-app-bar-nav-icon @click="$emit('toggle-drawer')" />
    <v-icon>mdi-library-books</v-icon>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title>
          <span class="font-weight-bold grey--text">
            Bibliotheca v{{ appVersion }}
          </span>
        </v-list-item-title>
        <v-list-item-subtitle>
          <span class="text-subtitle-2" v-if="isAdmin">
            <v-icon small color="red">mdi-account</v-icon>
            {{ "Admin account" }}
          </span>
          <span class="text-subtitle-2" v-else>
            <v-icon
                small
                v-text="validMembership ? 'mdi-check-circle' : 'mdi-close-circle'"
                :color="validMembership ? 'green' : 'red'"
            />
            <span>
              {{
                validMembership
                    ? "Membership valid"
                    : "Membership invalid"
              }}
            </span>
          </span>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-spacer />
    <v-progress-linear
        :active="loading"
        :indeterminate="loading"
        absolute
        bottom
    />
  </v-app-bar>
</template>

<script>
import UserMixin from "../mixins/userMixin";
import LoadingMixin from "../mixins/loadingMixin";
import MembershipMixin from "../mixins/membershipMixin";

export default {
  name: "app-bar",
  mixins: [UserMixin, LoadingMixin, MembershipMixin],
  data: () => ({
    appVersion: process.env.PACKAGE_VERSION,
  })
};
</script>
