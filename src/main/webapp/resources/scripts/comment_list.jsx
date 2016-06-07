var CommentList = React.createClass({
    render: function () {
      var commentNodes = this.props.data.map(function(comment) {
        return (
          <Comment name={comment.name} key={comment.name} >{comment.age}<hr/></Comment>
        )
      });
      return (
        <div className="commentList">
          {commentNodes}
        </div>
      )
  }
});

window.CommentList = CommentList;
