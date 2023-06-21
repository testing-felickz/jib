/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package example;

import com.google.common.io.CharStreams;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.Arrays;

public class HelloWorld {

  public static void main(String[] args) throws URISyntaxException, IOException {
    try (Reader reader = new InputStreamReader(
        HelloWorld.class.getResourceAsStream("/world"), StandardCharsets.UTF_8)) {
      String world = CharStreams.toString(reader);
      System.out.println("Hello " + world);
    }
  }

  // BAD: AES-GCM with static IV from a byte array
   public byte[] encryptWithStaticIvByteArrayWithInitializer(byte[] key, byte[] plaintext) throws Exception {
      byte[] iv = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5 };

      GCMParameterSpec ivSpec = new GCMParameterSpec(128, iv);
      SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

      Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec); // $staticInitializationVector
      cipher.update(plaintext);
      return cipher.doFinal();
  }
  
}
