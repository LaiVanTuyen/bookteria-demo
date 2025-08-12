package com.vti.post.mapper;

import com.vti.post.dto.response.PostResponse;
import com.vti.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
