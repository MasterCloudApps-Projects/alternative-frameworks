import { gql } from "apollo-server-express";

export const getPostsOperation = gql(`{
    posts {
        id
        user
        title
        text
      }
  }`
);
  
export const addPostOperation = gql(`mutation($post: PostInput!) {
  createPost(post: $post) {
      id
      user
      title
      text
    }
  }`
);

export const getPostOperation = gql(`query($id: Int!) {
  post(id: $id) {
      id
      user
      title
      text
    }
  }`
);

export const updatePostOperation = gql(`mutation($id: Int!, $post: PostInput!) {
  replacePost(id: $id, post: $post) {
      id
      user
      title
      text
    }
  }`
);

export const deletePostOperation = gql(`mutation($id: Int!) {
  deletePost(id: $id) {
      id
      user
      title
      text
    }
  }`
);