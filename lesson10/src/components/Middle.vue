<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index :posts="posts" :comments="comments" :users="users" v-if="page === 'Index'"/>
      <Post :post="showPost" :comments="comments" :users="users" v-if="page === 'Post'" :key="showPost.id"/>
      <Enter v-if="page === 'Enter'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <EditPost v-if="page === 'EditPost'"/>
      <Register v-if="page === 'Register'"/>
      <Users :viewUsers="viewUsers" v-if="page === 'Users'"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import Users from "@/components/middle/Users";
import Post from "@/components/middle/Post";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index"
    }
  },
  components: {
    Users,
    Post,
    Register,
    WritePost,
    Enter,
    Index,
    Sidebar,
    EditPost
  },
  props: ["posts", "comments", "users"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    },
    viewUsers: function () {
      return Object.values(this.users).sort((a, b) => b.id - a.id);
    }
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => {
      this.page = page;
      this.$forceUpdate();
    });
    this.$root.$on("showPost", (showPost) => {
      this.showPost = showPost;
      this.$root.$emit("onChangePage", 'Post');
    })
  }
}
</script>

<style scoped>

</style>