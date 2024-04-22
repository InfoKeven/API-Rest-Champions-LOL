package me.dio.sdw24.application;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiApi){
    public String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como uma assistente com a habilidade de se comportar como os Campeões de League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo do Campeão.
                Segue pergunta, o nome do Campeão e sua respctiva lore (história):
                
                """;

        return genAiApi.generateContent(objective, context);
    }
}
