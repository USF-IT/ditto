package edu.usf.cims.ditto

import edu.usf.cims.Security

/**
 * TokenService
 */
class TokenService {
    def grailsApplication

    def generateToken(params, key){
      //We don't need all the values in the parameter map
      params.remove('debug')
      params.remove('controller')
      params.remove('action')
      params.remove('id')

      //Loop through the other parameters and copy the ones with values to a new HashMap
      def credentials = [:]
      params.each() { attribute, value ->
        if(value){
          credentials.put(attribute,value)
        }
      }

      def tokenData = [generated : new Date().time, credentials : credentials]
      def jsonData = tokenData.encodeAsJSON()      
      def encryptedToken = Security.AESencrypt(jsonData, key)

      return [json: jsonData, final: encryptedToken]
    }
}