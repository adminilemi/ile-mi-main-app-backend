package com.thistlestechnology.ilemimainapp.serviceImplementation;

public class AdvertiserServiceImpl {
//    public UpdateCustomerResponse updateProfile(UpdateCustomerRequest updateCustomerRequest, HttpServletRequest servletRequest) throws JsonPatchException {
//        String userId = tokenVerifier(servletRequest);
//        System.out.println("___----------------->" + userId);
//        Customer customer = findById(userId);
//        ModelMapper modelMapper = new ModelMapper();
//        JsonPatch updatePatch = buildUpdatePatch(updateCustomerRequest);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode customerNode = objectMapper.convertValue(customer, JsonNode.class);
//        JsonNode updatedNode = updatePatch.apply(customerNode);
//        Customer updatedCustomer = objectMapper.convertValue(updatedNode, Customer.class);
//
//        customerRepository.save(updatedCustomer);
//        modelMapper.map(updateCustomerRequest, customer);
//
//
//        JsonPatch updatedPatch = buildUpdatePatch(updateCustomerRequest);
//        return applyPatch(updatedPatch, customer);
//    }
//
//    private ReplaceOperation buildReplaceOperation(UpdateCustomerRequest updateCustomerRequest, Field field) {
//        field.setAccessible(true);
//        try {
//            String path = JSON_PATCH_PATH_PREFIX + field.getName();
//            JsonPointer pointer = new JsonPointer(path);
//            var value = field.get(updateCustomerRequest);
//            TextNode node = new TextNode(value.toString());
//            return new ReplaceOperation(pointer, node);
//        } catch (Exception exception) {
//            throw new FiddoveaException(exception.getMessage());
//        }
//    }

//    private UpdateCustomerResponse applyPatch(JsonPatch updatePatch, Customer customer) throws JsonPatchException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        //1. Convert user to JsonNode
//        JsonNode userNode = objectMapper.convertValue(customer, JsonNode.class);
//
//        //2. Apply patch to JsonNode from step 1
//        JsonNode updatedNode = updatePatch.apply(userNode);
//        //3. Convert updatedNode to user
//        customer = objectMapper.convertValue(updatedNode, Customer.class);
//
//        String email = customer.getEmail().toLowerCase();
//        customer.setEmail(email);
//        //4. Save updatedUser from step 3 in the DB
//        var savedUser= customerRepository.save(customer);
//        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse();
//        updateCustomerResponse.setMessage(PROFILE_UPDATE_SUCCESSFUL.getMessage());
//        BeanUtils.copyProperties(savedUser, updateCustomerResponse);
//        return updateCustomerResponse;
//
//    }
//    private boolean validateField(UpdateCustomerRequest updateCustomerRequest, Field field) {
//        field.setAccessible(true);
//
//        try {
//            return field.get(updateCustomerRequest) != null;
//        } catch (IllegalAccessException e) {
//            throw new FiddoveaException(e.getMessage());
//        }
//    }
//
//    private JsonPatch buildUpdatePatch(UpdateCustomerRequest updateCustomerRequest) {
//        Field[] fields = updateCustomerRequest.getClass().getDeclaredFields();
//        List<ReplaceOperation> operations = Arrays.stream(fields)
//                .filter(field -> validateField(updateCustomerRequest, field))
//                .map(field -> buildReplaceOperation(updateCustomerRequest, field))
//                .toList();
//        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
//        return new JsonPatch(patchOperations);
//    }
}
