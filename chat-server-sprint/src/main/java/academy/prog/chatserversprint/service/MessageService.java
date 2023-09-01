package academy.prog.chatserversprint.service;

import academy.prog.chatserversprint.model.FileDTO;
import academy.prog.chatserversprint.model.Message;
import academy.prog.chatserversprint.model.MessageDTO;
import academy.prog.chatserversprint.repo.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void add(MessageDTO messageDTO) {
        var message = Message.fromDTO(messageDTO);
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> get(long id) {
        var messages = messageRepository.findNew(id);
        var result = new ArrayList<MessageDTO>();

        messages.forEach(message -> result.add(message.toDTO()));
        return result;
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getPrivate(String to) {
        var messages = messageRepository.findPrivate(to);
        var result = new ArrayList<MessageDTO>();

        messages.forEach(message -> result.add(message.toDTO()));
        return result;
    }

    @Transactional(readOnly = true)
    public FileDTO findFile(long id) throws UnsupportedEncodingException {
        var message = messageRepository.findFile(id);
        var result = new FileDTO();
        result.setFileData(message.getFileData());
        result.setFileName(message.getFileName());
        result.setId(message.getId());
        byte[] fileData = Base64.getEncoder().encode(message.getFileData().getBytes("UTF-8"));
        result.setFileDataByte(fileData);

        return result;
    }

    @Transactional
    public FileDTO makeFileDataByteArray(long id){
        var message = messageRepository.findFile(id);
        var result = new FileDTO();
        result.setFileDataByte(message.getFileData().getBytes());

        return result;
    }
}
