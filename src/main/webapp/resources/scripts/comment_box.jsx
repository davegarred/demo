var CommentBox = React.createClass({
    loadCommentsFromServer: function() {
      $.ajax({
        url: this.props.url,
        dataType: 'json',
        cache: false,
        success: function(data) {
          this.setState({data: data});
        }.bind(this),
        error: function(xhr,status,err) {
          console.error(this.props.url, status, err.toString());
        }.bind(this)
      })
    },
    getInitialState: function() {
      return {data: []};
    },
    componentDidMount: function() {
      this.loadCommentsFromServer();
      setInterval(this.loadCommentsFromServer, this.props.pollInterval);
    },
    handleCommentSubmit: function(comment) {
      $.ajax({
        url: this.props.url,
        dataType: 'json',
        type: 'POST',
        data: comment,
        success: function(data) {
          this.setState({data: data});
        }.bind(this),
        error: function(xhr,status,err) {
          console.log(this.props.url, status, err);
        }.bind(this)
      });
    },
    render: function() {
      return (
        <div className="commentBox">
          <h1>Comments</h1>
          <CommentList data={this.state.data} />
          <CommentForm onCommentSubmit={this.handleCommentSubmit} />
        </div>
      )
    }
});

var CommentForm = React.createClass({
    getInitialState: function() {
      return {author: '', text: ''};
    },
    handleAuthorChange: function(e) {
      this.setState({author: e.target.value});
    },
    handleTextChange: function(e) {
      this.setState({text: e.target.value});
    },
    handleSubmit: function(e) {
      e.preventDefault();
      this.props.onCommentSubmit(this.state);
      this.setState({author: '', text: ''});
    },
    render: function () {
    return (
      <form className="commentForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="name" value={this.state.author} onChange={this.handleAuthorChange} />
        <input type="text" placeholder="a comment..." value={this.state.text} onChange={this.handleTextChange}  />
        <input type="submit" placeholder="Post"/>
      </form>
    )
  }
});

var data = [
    {id: 1, author: "Author A", text: "This is one comment"},
    {id: 2, author: "Author B", text: "This is a second comment"}
];

ReactDOM.render(
  <CommentBox url="../data" pollInterval={2000} />,
  document.getElementById('content')
);
