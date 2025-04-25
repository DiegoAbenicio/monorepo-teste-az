<template>
  <v-app>
    <v-main class="main-container">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
};
</script>
<style>
.main-container {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #000000;
  background-color: #092334;
}
</style>