import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters(["memberships", "validMembership"])
  },
  methods: {
    ...mapActions(["setMemberships"])
  }
}