/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GaloisTest;

import galois.objects.graph.GNode;
import galois.runtime.Iteration;
import galois.runtime.MapInternalContext;
import java.util.concurrent.atomic.AtomicReference;
import util.fn.Lambda2Void;
import util.fn.Lambda3Void;
import util.fn.LambdaVoid;

/**
 *
 * @author nuno
 */
public class GTNode<Object> implements GNode<Object> {

    Object data;

    public GTNode(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GTNode{" + "data=" + data + '}';
    }

    public Object getData() {
        return data;
    }

    public Object getData(byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getData(byte nodeFlags, byte dataFlags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object setData(Object d) {
        data = d;
        return data;
    }

    public Object setData(Object d, byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRid(long rid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getRid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public AtomicReference<Iteration> getOwner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mapInternal(LambdaVoid<GNode<Object>> body, MapInternalContext ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1> void mapInternal(Lambda2Void<GNode<Object>, A1> body, MapInternalContext ctx, A1 arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1, A2> void mapInternal(Lambda3Void<GNode<Object>, A1, A2> body, MapInternalContext ctx, A1 arg1, A2 arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mapInternalDone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void map(LambdaVoid<GNode<Object>> body) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void map(LambdaVoid<GNode<Object>> body, byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1> void map(Lambda2Void<GNode<Object>, A1> body, A1 arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1> void map(Lambda2Void<GNode<Object>, A1> body, A1 arg1, byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1, A2> void map(Lambda3Void<GNode<Object>, A1, A2> body, A1 arg1, A2 arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <A1, A2> void map(Lambda3Void<GNode<Object>, A1, A2> body, A1 arg1, A2 arg2, byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void access(byte flags) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
