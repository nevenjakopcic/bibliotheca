<template>
  <v-row justify="center">
    <v-col cols="12">
      <h1 class="red--text">
        You have encountered an error. Please go back to the home page.
      </h1>
    </v-col>
    <v-col cols="12">
      <v-btn small color="primary" :to="{ name: RouteNames.ERROR }">
        Home
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import RouteNames from "../router/routeNames";

export default {
  name: "Error",
  data: () => ({
    RouteNames
  })
};
</script>
