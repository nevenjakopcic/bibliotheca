<template>
  <v-dialog
    persistent
    v-model="open"
    :max-width="maxWidth"
    :fullscreen="$vuetify.breakpoint.xs"
  >
    <v-card :style="{ maxHeight: maxHeight }">
      <v-card-title>
        <span
          :style="{
            fontSize:
              $vuetify.breakpoint.smAndDown ? '15px' : '20px'
          }"
        >
          {{ title }}
        </span>
        <v-spacer />
        <v-btn
          v-if="haveCloseButton"
          @click="close"
          small
          icon
          class="mr-3"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-divider class="pb-3" />
      <v-card-text>
        <slot />
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "header-dialog",
  props: {
    maxWidth: {
      type: String,
      default: "500px"
    },
    maxHeight: null,
    value: Boolean,
    title: String,
    haveCloseButton: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    close() {
      this.open = false;
      this.$emit("input", this.open);
      this.$emit("close");
    }
  },
  watch: {
    value(val) {
      this.open = val;
    }
  },
  data: () => ({
    open: false
  })
};
</script>
