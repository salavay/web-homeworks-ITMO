<template>
  <div class="posts">
    <article>
      <a class="titleLink" @click.prevent="showPost()">
        <div class="title">{{ post.title }}</div>
      </a>
      <div class="information">By {{ users[post.userId].login }}</div>
      <div class="body">{{ post.text }}</div>
      <div class="footer">
        <div class="left">
          <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
          <span class="positive-score">+173</span>
          <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
          <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
          24:00
          <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
          <a> {{ comments.length }} </a>
        </div>
      </div>
    </article>

    <template v-if="renderComments">
      <table class="commentMain" v-for="comment in comments" :key="comment.id">
        <tbody>
        <tr>
          <td class="commentTitle">By {{ users[comment.userId].login }}:</td>
          <td class="commentText">{{ comment.text }}</td>
        </tr>
        </tbody>
      </table>
    </template>
  </div>
</template>

<script>
export default {
  name: "MiddlePost",
  props: ["post", "comments", "users", "renderComments"],
  methods: {
    showPost: function () {
      this.$root.$emit("showPost", this.post);
    }
  }
}
</script>

<style scoped>
main .commentMain {
  margin-top: 1rem;
}


main .commentTitle {
  font-size: 1.3rem;
  color: var(--caption-color);
  font-weight: bold;
}

main .commentTime {

}

main .commentText {
  font-size: 1.4rem;
}

main .commentInput {
  max-width: 100%;
}
</style>