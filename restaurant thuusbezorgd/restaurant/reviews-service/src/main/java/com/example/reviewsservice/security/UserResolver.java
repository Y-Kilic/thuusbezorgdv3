package com.example.reviewsservice.security;

/**
 * Dit is natuurlijk -juist geen- security, maar ik merkte dat hier nette voorbeeldcode van aanleveren meer was dan de rest
 * van de applicatie bij elkaar. Dus dat leek me een beetje onhandig
 */
public class UserResolver /*implements HandlerMethodArgumentResolver*/ {

//    private final UserRepository users;
//
//    public UserResolver(UserRepository users) {
//        this.users = users;
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.getParameter().getType() == User.class;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
////        String username = webRequest.getHeader("Authentication-Hack");
//        String username = "Muzammil";
//
//        Exception unauthorized = new ResponseStatusException(HttpStatus.UNAUTHORIZED,
//                "No user found, use Authentication-Hack header");
//        if (username != "Muzammil") {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
//                    "No user found, use Authentication-Hack header");
//        }
//        Optional<User> result = users.findById(username);
//
//        return result.orElseThrow(() -> unauthorized);
//    }
}
