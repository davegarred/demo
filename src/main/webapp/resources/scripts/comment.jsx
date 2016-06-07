var Comment = React.createClass({
  render: function() {
    return (
      <div className="comment">
          {this.props.name} - {this.props.children}
      </div>
    )
  }
});

window.Comment = Comment;
