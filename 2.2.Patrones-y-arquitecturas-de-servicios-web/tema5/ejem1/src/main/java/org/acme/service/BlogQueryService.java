package org.acme.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.acme.model.Post;
import org.acme.repository.PostRepository;
import org.acme.resource.BasicPostDto;
import org.acme.resource.FullPostDto;

@ApplicationScoped
public class BlogQueryService {

	@Inject
	PostRepository postRepository;
	
	ModelMapper modelMapper = new ModelMapper();	
	
	public List<BasicPostDto> getPostsList() {
		return postRepository.findAll()
				.stream()
				.map((post) -> convertPostEntityToDto(post))
				.collect(Collectors.toList());
	}

	public FullPostDto getPost(long id) {
		Post post = postRepository.findById(id);
		if(post != null) {
			return convertPostEntityToPostFullDto(post);
		}
		return null;
	}

	private FullPostDto convertPostEntityToPostFullDto(Post post) {
		return modelMapper.map(post, FullPostDto.class);
	}

	private BasicPostDto convertPostEntityToDto(Post post) {
		return modelMapper.map(post, BasicPostDto.class);
	}


}
