/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.shelly.domino.converter;

import retrofit2.Response;
import xiaofei.library.shelly.domino.Domino;
import xiaofei.library.shelly.domino.RetrofitDomino;
import xiaofei.library.shelly.tuple.Triple;

/**
 * Created by Xiaofei on 16/6/27.
 */
public class RetrofitDominoConverter<T, R> extends
        DominoConverter<T, Triple<Boolean, Response<R>, Throwable>, RetrofitDomino<T, R>> {
    @Override
    public RetrofitDomino<T, R> call(Domino<T, Triple<Boolean, Response<R>, Throwable>> domino) {
        return new RetrofitDomino<T, R>(domino.getLabel(), domino.getPlayer());
    }
}
