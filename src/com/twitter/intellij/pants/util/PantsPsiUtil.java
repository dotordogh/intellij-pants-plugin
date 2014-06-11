package com.twitter.intellij.pants.util;

import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.python.psi.PyArgumentList;
import com.jetbrains.python.psi.PyCallExpression;
import com.jetbrains.python.psi.PyExpressionStatement;
import com.jetbrains.python.psi.PyKeywordArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajohnson on 6/9/14.
 */
public class PantsPsiUtil {

  public static List<Target> findTargets(@NotNull PsiFile file) {
    List<Target> targets = new ArrayList<Target>();
    for (PyExpressionStatement statement : PsiTreeUtil.findChildrenOfType(file, PyExpressionStatement.class)) {
      Target target = findTarget(statement);
      if (target != null) {
        targets.add(target);
      }
    }
    return targets;
  }

  @Nullable
  private static Target findTarget(PyExpressionStatement statement) {
    for (PyCallExpression expression : PsiTreeUtil.findChildrenOfType(statement, PyCallExpression.class)) {
      for (PyArgumentList args : PsiTreeUtil.findChildrenOfType(expression, PyArgumentList.class)) {
        PyKeywordArgument arg = args.getKeywordArgument("name");
        if (arg != null) {
          return new Target(arg.getValueExpression().toString(), expression.toString());
        }
      }
    }
    return null;
  }


}