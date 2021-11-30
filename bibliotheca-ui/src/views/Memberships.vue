<template>
  <div>
    <v-data-table
        :headers="headers"
        :items="users"
        class="elevation-4"

        hide-default-footer
    >
      <template #item.username="{ item }">
        {{ item.username }}
      </template>
      <template #item.actions="{ item }">
        <v-btn v-if="isAdmin"
               small
               color="success"
               class="mr-2"
               @click="extendMembership(item.membership.id)"
        >
          {{ "Extend membership" }}
        </v-btn>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import UserMixin from "../mixins/userMixin";
import MembershipMixin from "../mixins/membershipMixin";
import LoadingMixin from "../mixins/loadingMixin";
import routeNames from "../router/routeNames";
import UserService from "../services/userService";
import MembershipService from "../services/membershipService";

export default {
  name: "Home",
  mixins: [UserMixin, MembershipMixin, LoadingMixin],
  async created() {
    await this.getUsers();
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (!vm.validMembership) {
        vm.$emit("show-snackbar", {
          color: "error",
          message: "Extend your membership to gain access",
        });
        next({name: routeNames.HOME});
      } else {
        next();
      }
    });
  },
  methods: {
    async getUsers() {
      this.setLoading(true);
      const {
        data: {data}
      } = await UserService.getAllUsers();
      this.users = data;
      this.setLoading(false);
    },
    async extendMembership(membershipId) {
      try {
        await MembershipService.extendMembership(membershipId);
        this.$emit("show-snackbar", {
          color: "success",
          message: "Membership extended"
        });
      } catch (e) {
        this.$emit("show-snackbar", {
          color: "error",
          message: "Membership extension failed"
        });
      } finally {
        await this.getUsers();
      }
    },
  },
  data() {
    return {
      search: '',
      users: []
    }
  },
  computed: {
    headers() {
      return [
        {
          text: 'Username',
          align: 'start',
          sortable: false,
          value: 'username'
        },
        {
          text: 'Valid until',
          value: 'membership.validUntil'
        },
        {
          text: "",
          align: 'center',
          sortable: false,
          value: "actions"
        }
      ]
    }
  }
};
</script>