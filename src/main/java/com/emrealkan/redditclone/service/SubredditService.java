package com.emrealkan.redditclone.service;

import com.emrealkan.redditclone.dto.SubredditDto;
import com.emrealkan.redditclone.exceptions.SpringRedditException;
import com.emrealkan.redditclone.mapper.SubredditMapper;
import com.emrealkan.redditclone.model.Subreddit;
import com.emrealkan.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
      Subreddit subreddit =  subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
      subreddit.setId(subreddit.getId());
      return subredditDto;
    }


    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }



}
