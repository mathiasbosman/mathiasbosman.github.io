package be.mathiasbosman.cv.service;

import be.mathiasbosman.cv.dto.PostContentDto;
import be.mathiasbosman.cv.dto.PostDto;
import be.mathiasbosman.cv.dto.UserDto;
import be.mathiasbosman.cv.entity.Post;
import be.mathiasbosman.cv.repo.PostRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(propagation = Propagation.NEVER)
public class PostServiceImpl implements PostService {

  private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
  private final PostRepository postRepository;
  private final UserService userService;

  public PostServiceImpl(PostRepository postRepository, UserService userService) {
    this.postRepository = postRepository;
    this.userService = userService;
  }

  @Override
  @Transactional
  public PostDto delete(UUID postId) {
    log.info("Deleting post [{}]", postId);
    Post p = getPost(postId);
    p.setDeleted(true);
    return getPostDto(p);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostDto> getPosts() {
    return postRepository.findAll(false).stream().map(this::getPostDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<PostDto> getPosts(UUID posterId) {
    return getPosts(posterId, false);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostDto> getPosts(UUID posterId, boolean deleted) {
    return postRepository.findAllByPosterIdAndDeleted(posterId, deleted).stream()
        .map(this::getPostDto).collect(Collectors.toList());
  }

  @Override
  public PostDto post(PostContentDto contentDto, UUID userId) {
    // should we revalidate?
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  @Transactional(readOnly = true)
  public boolean validate(PostContentDto contentDto) {
    return StringUtils.hasLength(contentDto.getBody()) && StringUtils
        .hasLength(contentDto.getSubject());
  }


  private PostDto getPostDto(Post p) {
    UserDto u = userService.getUser(p.getPosterId());
    return new PostDto(p.getId(), p.getSubject(), p.getBody(), u, p.getCreated(), p.getUpdated());
  }

  private Post getPost(UUID id) {
    return postRepository.getOne(id);
  }
}
