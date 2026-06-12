package org.example.Service;

import org.example.Exception.NotFoundException;
import org.example.Model.Command;
import org.example.Model.Product;
import org.example.Repositoriy.CommandRepository;

public class CommandService {

    private final CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public Command addProductToCommand(Product product,int id) {
        Command commandFound = commandRepository.findById(id);
        if(commandFound != null){
            if(commandFound.getProductsQuantity().containsKey(product)){
                int quantity = commandFound.getProductsQuantity().get(product) ;
                commandFound.replaceProductQuantity(product,++quantity);
            }else{
                commandFound.addProductQuantity(product,1);
            }
            return commandFound;
        }
        throw new NotFoundException();
    }

    public Command removeProductToCommand(Product product,int id) {
        Command commandFound = commandRepository.findById(id);
        if(commandFound != null){
            if(commandFound.getProductsQuantity().containsKey(product)){
                int quantity = commandFound.getProductsQuantity().get(product) ;
                if(quantity>1){
                    commandFound.replaceProductQuantity(product,--quantity);
                }else{
                    commandFound.removeProduct(product);
                }
            }else{
                throw new NotFoundException();
            }
            return commandFound;
        }
        throw new NotFoundException();
    }

    public Command updateCommand (Command command){
        commandRepository.update(command);
        return command;
    }
}
