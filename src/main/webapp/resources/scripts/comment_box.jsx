var CommentBox = React.createClass({
    loadCommentsFromServer: function() {
      $.ajax({
        url: this.props.url,
        dataType: 'json',
        cache: false,
        success: function(data) {
//            debugger;
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
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(comment),
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
          <CommentForm onCommentSubmit={this.handleCommentSubmit} />
          <CommentList data={this.state.data} />
        </div>
      )
    }
});

var CommentForm = React.createClass({
    getInitialState: function() {
      return {name: '', age: ''};
    },
    handleNameChange: function(e) {
      this.setState({name: e.target.value});
    },
    handleAgeChange: function(e) {
      this.setState({age: e.target.value});
    },
    handleSubmit: function(e) {
      e.preventDefault();
      this.props.onCommentSubmit(this.state);
      this.setState({name: '', age: ''});
    },
    render: function () {
    return (
      <form className="commentForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="name" value={this.state.name} onChange={this.handleNameChange} />
        <input type="text" placeholder="age" value={this.state.age} onChange={this.handleAgeChange}  />
        <input type="submit" placeholder="Put"/>
      </form>
    )
  }
});

ReactDOM.render(
  <CommentBox url="../data" pollInterval={2000} />,
  document.getElementById('content')
);
